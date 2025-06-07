package com.intellectus.backend.auxiliar;

public class Comparador {

    private Comparador() {
    }

    public static boolean cumpleCondicion(String valorGuardado, Double valorComparar) {
        try {
            if (valorGuardado == null || valorGuardado.isEmpty()) {
                return false;
            }
            if (valorGuardado.startsWith(">=")) {
                double valor = Double.parseDouble(valorGuardado.substring(2));
                return valorComparar != null && valorComparar >= valor;
            }
            if (valorGuardado.startsWith(">")) {
                double valor = Double.parseDouble(valorGuardado.substring(1));
                return valorComparar != null && valorComparar > valor;
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    public static boolean cumpleListado(String valorGuardado, String linea) {
        if (linea.equals("Otro")) {
            return true;
        } else {
            String[] partes = linea.split(",");
            for (String parte : partes) {
                if (valorGuardado.equals(parte)) {
                    return true;
                }
            }
            return false;
        }
    }
}