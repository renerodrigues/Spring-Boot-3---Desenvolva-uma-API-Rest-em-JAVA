package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.infra.Exception.ValidacaoException;
@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamentoDeConsultas {
   
    public void validar(DadosAgendamentoConsulta dados) {
        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();
        var difarencaEmMinutos = Duration.between(agora, dataConsulta).toMinutes();
        if (difarencaEmMinutos < 30)
            throw new ValidacaoException("A consulta deve ser agendada com antecedencia minima de 30 minutos");
    }
}
