package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import med.voll.api.domain.medico.DadosAtualizacaoMedico;
import med.voll.api.domain.medico.DadosCadastroMedico;
import med.voll.api.domain.medico.DadosDetalhamentoMedico;
import med.voll.api.domain.medico.DadosListagemMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional // do pacote spring. para fazer o insert no banco de dados é necessario ter uma
                   // transação atia com o banco de dados
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBilder) {
        var medico = new Medico(dados);
        repository.save(medico);
        /**
         * para cadastro, o padrão de resposta http é o codigo 201, tambem é necessario
         * devolver no corpo da resposta os dados do novo recurso/registro criado e
         * também um cabecalho do protocolo http (location "endereço para o novo
         * registro")
         */
        var uri = uriBilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    // @GetMapping
    // public List<DadosListagemMedico> listar(){
    // return repository.findAll().stream().map(DadosListagemMedico::new).toList();
    // // exlicação na alura no curso de Spring Boot 3: desenvolva uma API Rest
    // Java, parte 04.Requisções GET aula 01 produção de dados na API
    // }
    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(
            @PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        /**
         * para definir tamanh dos elementos por pagina deve se adicionar a palavra size
         * na url com a quantidade de elementos que deseja. Ex:
         * http://localhost:8080/medicos?size=1
         * http://localhost:8080/medicos?size=1&page2
         * http://localhost:8080/medicos?sort=nome,desc ordena por algum nome da
         * entidade exlicação na alura no curso de Spring Boot 3: desenvolva uma API
         * Rest Java, parte 04.Requisções GET aula 01 produção de dados na API
         */
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);

    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoMedico dados) {
        var medico = repository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable long id) {
        var medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable long id) {
        var medico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
