package com.boleto.api.factory;


import br.com.caelum.stella.boleto.*;
import br.com.caelum.stella.boleto.bancos.Bancos;
import br.com.caelum.stella.boleto.bancos.Santander;
import org.springframework.stereotype.Service;

@Service
public class SantanderFactory implements FactoryResolver.BancoFactory {

    @Override
    public boolean match(String numeroBanco) {
        return Bancos.SANTANDER.getNumeroDoBanco().equals(numeroBanco);
    }

    public Boleto create(){
        Banco banco = new Santander();

        Datas datas = Datas
                .novasDatas()
                .comDocumento(1, 5, 2008)
                .comProcessamento(1, 5, 2008)
                .comVencimento(2, 5, 2008);

        Endereco enderecoBeneficiario = Endereco
                .novoEndereco()
                .comLogradouro("Av das Empresas, 555")
                .comBairro("Bairro Grande")
                .comCep("01234-555")
                .comCidade("São Paulo")
                .comUf("SP");

        Beneficiario beneficiario = Beneficiario
                .novoBeneficiario()
                .comNomeBeneficiario("PETROBRAS")
                .comEndereco(enderecoBeneficiario)
                .comAgencia("6790")
                .comDigitoAgencia("0")
                .comCarteira("102")
                .comNumeroConvenio("5260965")
                .comNossoNumero("105613749501")
                .comDigitoNossoNumero("4");

        Endereco enderecoPagador = Endereco
                .novoEndereco()
                .comLogradouro("Av dos testes, 111 apto 333")
                .comBairro("Bairro Teste")
                .comCep("01234-111")
                .comCidade("São Paulo")
                .comUf("SP");

        Pagador pagador = Pagador
                .novoPagador()
                .comNome("PAULO SILVEIRA")
                .comEndereco(enderecoPagador) ;

        Boleto boleto = Boleto
                .novoBoleto()
                .comEspecieDocumento("DM")
                .comBanco(banco)
                .comDatas(datas)
                .comBeneficiario(beneficiario)
                .comPagador(pagador)
                .comValorBoleto(219.50)
                .comAceite(Boolean.FALSE)
                .comInstrucoes("instrucao 1", "instrucao 2", "instrucao 3", "instrucao 4")
                .comLocaisDePagamento("local 1", "local 2")
                .comNumeroDoDocumento("105613749501");

        return boleto;
    }
}


