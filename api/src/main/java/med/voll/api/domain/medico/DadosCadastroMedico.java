package med.voll.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroMedico(
        @NotBlank(message = "O nome é obrigatório :(") // notblanck é somente para campos string
        String nome,

        @NotBlank @Email(message = "Formato do email é inválido") String email,

        @NotBlank String telefone,

        @NotBlank(message = "{crm.obrigatorio}") @Pattern(regexp = "\\d{4,6}", message = "{crm.invalido}") String crm,

        @NotNull Especialidade especialidade,

        @NotNull @Valid // valida tabem os atributos do objeto
        DadosEndereco endereco) {

    /**
     * Outra maneira é isolar as mensagens em um arquivo de propriedades, que deve
     * possuir o nome ValidationMessages.properties e ser criado no diretório
     * src/main/resources:
     */

}
