package com.boleto.api.controller;

import br.com.caelum.stella.boleto.Boleto;
import br.com.caelum.stella.boleto.bancos.Bancos;
import br.com.caelum.stella.boleto.exception.BancoNaoSuportadoException;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;
import com.boleto.api.factory.FactoryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/boleto")
public class BoletoController {

    @Autowired
    private FactoryResolver resolver;

    @GetMapping("/{numeroBanco}")
    public ResponseEntity<byte[]> novo(@PathVariable("numeroBanco") String numeroBanco){
        Boleto boleto = resolver.get(numeroBanco);

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = getPorNumero(numeroBanco) + ".pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(gerador.geraPDF(), headers, HttpStatus.OK);
        return response;
    }

    private String getPorNumero(String numero) {
        for (Bancos b : Bancos.values()) {
            if (b.getNumeroDoBanco().equals(numero))
                return b.getNomeDoBanco();
        }
        throw new BancoNaoSuportadoException(numero);
    }
}

