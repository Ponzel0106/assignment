package nl.gerimedica.assignment.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class AppointmentRouter {
    private final AppointmentHandler handler;

    @Bean
    public RouterFunction<ServerResponse> appointmentRouterFunction() {
        return route()
                .nest(
                        path("api/v1/appointments"),
                        () -> route()
                                .POST("/bulk", accept(MediaType.APPLICATION_JSON), handler::createAppointments)
                                .GET("/{ssnToken}/latest", handler::getLatestAppointment)
                                .GET("/{ssnToken}", handler::getAppointments)
                                .DELETE("/{ssnToken}", handler::deleteAppointments)
                                .build()
                ).build();
    }
}
