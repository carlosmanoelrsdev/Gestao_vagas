package br.com.CarlosManoel.Gestao_vagas.exceptions;

public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super("Vaga não encontrada");
    }
}
