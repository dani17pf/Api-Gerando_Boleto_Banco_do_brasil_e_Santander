package com.boleto.api.factory;

import br.com.caelum.stella.boleto.*;
import br.com.caelum.stella.boleto.bancos.BancoDoBrasil;
import br.com.caelum.stella.boleto.bancos.Bancos;
import org.springframework.stereotype.Service;

@Service
public class BancoDoBrasilFactory implements FactoryResolver.BancoFactory {

    @Override
    public boolean match(String numeroBanco) {
        return Bancos.BANCO_DO_BRASIL.getNumeroDoBanco().equals(numeroBanco);
    }

    public Boleto create(){
        Datas datas = Datas.novasDatas()
                .comDocumento(21, 6, 2022)
                .comProcessamento(21, 6, 2022)
                .comVencimento(25, 6, 2022);

        Endereco enderecoBeneficiario = Endereco.novoEndereco()
                .comLogradouro("Av das Empresas, 555")
                .comBairro("Bairro Grande")
                .comCep("99020-170")
                .comCidade("Passo Fundo")
                .comUf("RS");

        //Quem emite o boleto
        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("Daniel")
                .comAgencia("1824").comDigitoAgencia("4")
                .comCodigoBeneficiario("76000")
                .comDigitoCodigoBeneficiario("5")
                .comNumeroConvenio("1207113")
                .comCarteira("18")
                .comEndereco(enderecoBeneficiario)
                .comNossoNumero("90002061234512345");

        Endereco enderecoPagador = Endereco.novoEndereco()
                .comLogradouro("Av dos testes, 111 apto 333")
                .comBairro("Bairro Teste")
                .comCep("01234-111")
                .comCidade("Passo Fundo")
                .comUf("RS");

        //Quem paga o boleto
        Pagador pagador = Pagador.novoPagador()
                .comNome("Fulano da Silva")
                .comDocumento("111.222.333-12")
                .comEndereco(enderecoPagador);

        Banco banco = new BancoDoBrasil();

        Boleto boleto = Boleto.novoBoleto()
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto("200.00")
                .comNumeroDoDocumento("1234")
                .comInstrucoes("instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4", "instrucao 5")
                .comLocaisDePagamento("Passo Fundo", "Loterica centro");

        return boleto;
    }
}