package br.com.idonate.iDonate.resource;

import br.com.idonate.iDonate.model.Cotacao;
import br.com.idonate.iDonate.service.CotacaoService;
import br.com.idonate.iDonate.service.exception.InvalidValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/v1/cotacao")
public class CotacaoResource {

    @Autowired
    CotacaoService cotacaoService;

    @PostMapping
    public ResponseEntity<Cotacao> save(@Valid @RequestBody Cotacao cotacao) throws InvalidValueException{
        Cotacao savedCotacao = cotacaoService.save(cotacao);
        return new ResponseEntity<>(savedCotacao, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cotacao> edit(@PathVariable Long id, @Valid @RequestBody Cotacao cotacao) throws InvalidValueException {
        Cotacao updateCotacao = cotacaoService.edit(id, cotacao);
        return new ResponseEntity<>(updateCotacao, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cotacao> searchById(@PathVariable Long id) {
        Optional<Cotacao> cotacao = cotacaoService.searchById(id);
        return (cotacao.isPresent() ? ResponseEntity.ok(cotacao.get()) : ResponseEntity.notFound().build());
    }
}