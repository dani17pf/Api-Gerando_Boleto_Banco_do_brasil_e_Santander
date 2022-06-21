package com.boleto.api.factory;

import br.com.caelum.stella.boleto.Boleto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryResolver {
    private List<BancoFactory> factories;

    @Autowired
    public FactoryResolver(List<BancoFactory> factories) {
        this.factories = factories;
    }

    public Boleto get(String numeroBanco){
        BancoFactory factory = factories
                .stream()
                .filter(bancoFactory -> bancoFactory.match(numeroBanco))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Banco não tem Factory"));

        return factory.create();
    }


    interface BancoFactory {
        boolean match(String numeroBanco);
        Boleto create();
    }
}
