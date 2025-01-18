package pt.ipleiria.estg.dei.ei.dae.academics.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.dtos.SensorTemperaturaDTO;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.SensorBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorAceleracao;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorPosicao;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.SensorTemperatura;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("sensors") // Relative URL web path for this service
@Produces({MediaType.APPLICATION_JSON}) // Content-Type: application/json
@Consumes({MediaType.APPLICATION_JSON}) // Accept: application/json
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @POST
    @Path("/posicao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensorPosicao(SensorDTO sensorDTO) {
        try {
            SensorPosicao sensorPosicao = sensorBean.createSensorPosicao(
                    sensorDTO.getName(),
                    sensorDTO.getVolume(),
                    sensorDTO.isEstado(),
                    sensorDTO.getValor() // Assuming `valor` represents `intervalo` for SensorPosicao
            );
            return Response.status(Response.Status.CREATED).entity(toDTO(sensorPosicao)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/aceleracao")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensorAceleracao(SensorDTO sensorDTO) {
        try {
            SensorAceleracao sensorAceleracao = sensorBean.createSensorAceleracao(
                    sensorDTO.getName(),
                    sensorDTO.getVolume(),
                    sensorDTO.isEstado(),
                    (int) sensorDTO.getValor() // Assuming `valor` represents `impactCount` for SensorAceleracao
            );
            return Response.status(Response.Status.CREATED).entity(toDTO(sensorAceleracao)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/temperatura")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSensorTemperatura(SensorDTO sensorDTO) {
        try {
            SensorTemperatura sensorTemperatura = sensorBean.createSensorTemperatura(
                    sensorDTO.getName(),
                    sensorDTO.getVolume(),
                    sensorDTO.isEstado(),
                    sensorDTO.getValor() // Assuming `valor` represents `intervalo` for SensorTemperatura
            );
            return Response.status(Response.Status.CREATED).entity(toDTO(sensorTemperatura)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    // Get all sensors
    @GET
    @Path("/")
    public List<SensorDTO> getAllSensors() {
        return sensorBean.getAllSensors().stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Get a sensor by ID
    @GET
    @Path("{sensorId}")
    public Response getSensorById(@PathParam("sensorId") int id) {
        Sensor sensor = sensorBean.findSensor(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Sensor not found").build();
        }
        return Response.ok(toDTO(sensor)).build();
    }

    // Create a generic sensor
    @POST
    @Path("/")
    public Response createSensor(SensorDTO sensorDTO) {
        try {
            Sensor sensor = sensorBean.createGenericSensor(
                    sensorDTO.getName(),
                    sensorDTO.getVolume(),
                    sensorDTO.isEstado()
            );
            return Response.status(Response.Status.CREATED).entity(toDTO(sensor)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Update a sensor
    @PUT
    @Path("{sensorId}")
    public Response updateSensor(@PathParam("sensorId") int id, SensorDTO sensorDTO) {
        try {
            sensorBean.updateSensor(id, sensorDTO.getVolume(), sensorDTO.isEstado());
            Sensor updatedSensor = sensorBean.findSensor(id);
            if (updatedSensor == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Sensor not found").build();
            }
            return Response.ok(toDTO(updatedSensor)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    // Delete a sensor
    @DELETE
    @Path("{sensorId}")
    public Response deleteSensor(@PathParam("sensorId") int id) {
        Sensor sensor = sensorBean.findSensor(id);
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Sensor not found").build();
        }
        sensorBean.deleteSensor(id);
        return Response.noContent().build();
    }

    // Detect an impact for SensorAceleracao
    @POST
    @Path("{sensorId}/detect-impact")
    public Response detectImpact(@PathParam("sensorId") int id, @QueryParam("acceleration") float acceleration) {
        Sensor sensor = sensorBean.findSensor(id);
        if (sensor instanceof SensorAceleracao) {
            boolean impactDetected = sensorBean.detectImpact(id, acceleration);
            return Response.ok("Impact detected: " + impactDetected).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Sensor is not of type SensorAceleracao").build();
    }

    // Get impact count for SensorAceleracao
    @GET
    @Path("{sensorId}/impact-count")
    public Response getImpactCount(@PathParam("sensorId") int id) {
        Sensor sensor = sensorBean.findSensor(id);
        if (sensor instanceof SensorAceleracao) {
            int impactCount = sensorBean.getImpactCount(id);
            return Response.ok("Impact count: " + impactCount).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("Sensor is not of type SensorAceleracao").build();
    }

    @POST
    @Path("/temperatura/{sensorId}/readings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTemperatureReading(@PathParam("sensorId") int sensorId, float temperature) {
        try {
            Sensor sensor = sensorBean.findSensor(sensorId);
            if (!(sensor instanceof SensorTemperatura)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Sensor ID " + sensorId + " is not a temperature sensor.")
                        .build();
            }

            // Assuming you have a method to handle adding readings for SensorTemperatura
            boolean success = sensorBean.addTemperatureReading(sensorId, temperature);
            if (success) {
                return Response.ok("Temperature reading added successfully.").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Failed to add temperature reading.")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/posicao/{sensorId}/readings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPositionReading(@PathParam("sensorId") int sensorId, float position) {
        try {
            Sensor sensor = sensorBean.findSensor(sensorId);
            if (!(sensor instanceof SensorPosicao)) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Sensor ID " + sensorId + " is not a position sensor.")
                        .build();
            }

            // Assuming you have a method to handle adding readings for SensorPosicao
            boolean success = sensorBean.addPositionReading(sensorId, position);
            if (success) {
                return Response.ok("Position reading added successfully.").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Failed to add position reading.")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{sensorId}/update-threshold")
    public Response updateThreshold(@PathParam("sensorId") int sensorId, SensorTemperaturaDTO sensorTemperaturaDTO) {
        try {
            sensorBean.updateSensorTemperaturaThreshold(sensorId, sensorTemperaturaDTO.getThreshold());
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error updating threshold").build();
        }
    }

    // Endpoint to add a temperature reading and check if it is below the threshold
    @POST
    @Path("/{sensorId}/reading")
    public Response addTemperatureReading(@PathParam("sensorId") int sensorId, SensorDTO sensorDTO) {
        boolean isBelowThreshold = sensorBean.registerTemperatureReading(sensorId, sensorDTO.getValor());
        if (isBelowThreshold) {
            return Response.status(Response.Status.CREATED)
                    .entity("Temperature reading added. Below threshold count incremented.")
                    .build();
        } else {
            return Response.status(Response.Status.CREATED)
                    .entity("Temperature reading added. Not below threshold.")
                    .build();
        }
    }

    // Endpoint to get the count of temperature readings below the threshold
    @GET
    @Path("/{sensorId}/below-threshold-count")
    public Response getBelowThresholdCount(@PathParam("sensorId") int sensorId) {
        int count = sensorBean.getBelowThresholdCount(sensorId);
        return Response.ok().entity("Below threshold count: " + count).build();
    }


    // Helper method to map a Sensor entity to a SensorDTO
    private SensorDTO toDTO(Sensor sensor) {
        SensorDTO dto = new SensorDTO();
        dto.setId(sensor.getId());
        dto.setName(sensor.getName());
        dto.setVolume(sensor.getVolume());
        dto.setEstado(sensor.isEstado());
        dto.setValor(sensor.getValor()); // Assuming Sensor entity has `getValor()`
        dto.setUltimaLeitura(sensor.getUltimaLeitura());
        return dto;
    }
}
