package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.AgendaConsultas;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
 
@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")  // anotação do Spring doc para o cabecalho Brarer para inserir o token JWT

public class ConsultaController {

    @Autowired
    private AgendaConsultas agenda;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
      var dto =  agenda.agendar(dados);
        return ResponseEntity.ok(dto);
    }
}