package com.queonetics.registration.messages;

public enum VehicleMessages {
    EXISTING_REGISTRATION("Esta placa já está sendo usada."),
    SUCCESS_DELETE("Veiculo removido com sucesso");

    private String message;

    VehicleMessages(String message){
        this.message = message;
    }

    public String getValue(){
        return this.message;
    }
}
