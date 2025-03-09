package es.pausegarra.russkiy_po_moyemu.auth.infrastructure.rest;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Arrays;
import java.util.HashSet;

@Path("/auth/login")
public class LoginResource {

  @GET
  public String login() {
    return Jwt.issuer("https://example.com/issuer")
        .upn("jdoe@quarkus.io")
        .groups(new HashSet<>(Arrays.asList("User", "Admin")))
        .claim(Claims.birthdate.name(), "2001-07-13")
        .sign();
  }

}
