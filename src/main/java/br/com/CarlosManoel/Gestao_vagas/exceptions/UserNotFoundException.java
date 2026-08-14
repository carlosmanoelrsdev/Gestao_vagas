package br.com.CarlosManoel.Gestao_vagas.exceptions;

public class UserNotFoundException  extends RuntimeException{
    public UserNotFoundException() {
        super("User not found");
    }
}
