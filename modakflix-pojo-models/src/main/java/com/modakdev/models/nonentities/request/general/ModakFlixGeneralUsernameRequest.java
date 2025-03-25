package com.modakdev.models.nonentities.request.general;

public class ModakFlixGeneralUsernameRequest {
    String modakFlixUsername;

    public ModakFlixGeneralUsernameRequest(String modakFlixUsername) {
        this.modakFlixUsername = modakFlixUsername;
    }

    public ModakFlixGeneralUsernameRequest() {
    }

    public String getModakFlixUsername() {
        return modakFlixUsername;
    }

    public void setModakFlixUsername(String modakFlixUsername) {
        this.modakFlixUsername = modakFlixUsername;
    }

    @Override
    public String toString() {
        return "ModakFlixGeneralUsernameRequest{" +
                "modakFlixUsername='" + modakFlixUsername + '\'' +
                '}';
    }
}
