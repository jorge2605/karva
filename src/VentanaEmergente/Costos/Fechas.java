package VentanaEmergente.Costos;

import javax.swing.JOptionPane;

public class Fechas{
    FF fecha;
    FF Enero2023 = new FF("2023-01-02","2023-01-29",4,1);
    FF Febrero2023 = new FF("2023-01-30","2023-02-26",4,5);
    FF Marzo2023 = new FF("2023-02-27","2023-04-02",5,9);
    FF Abril2023 = new FF("2023-04-03","2023-04-30",4,14);
    FF Mayo2023 = new FF("2023-05-01","2023-05-28",4,18);
    FF Junio2023 = new FF("2023-05-29","2023-07-02",5,22);
    FF Julio2023 = new FF("2023-07-03","2023-07-30",4,27);
    FF Agosto2023 = new FF("2023-07-31","2023-08-27",4,31);
    FF Septiembre2023 = new FF("2023-08-28","2023-10-01",5,35);
    FF Octubre2023 = new FF("2023-10-02","2023-10-29",4,40);
    FF Noviembre2023 = new FF("2023-10-30","2023-11-26",4,44);
    FF Diciembre2023 = new FF("2023-11-27","2023-12-31",5,48);
    
    FF Enero2024 = new FF("2024-01-01","2024-01-28",4,1);
    FF Febrero2024 = new FF("2024-01-29","2024-02-25",4,5);
    FF Marzo2024 = new FF("2024-02-26","2024-03-31",5,9);
    FF Abril2024 = new FF("2024-04-01","2024-04-28",4,14);
    FF Mayo2024 = new FF("2024-04-29","2024-06-02",5,18);
    FF Junio2024 = new FF("2024-06-03","2024-06-30",4,23);
    FF Julio2024 = new FF("2024-07-01","2024-08-04",5,27);
    FF Agosto2024 = new FF("2024-08-05","2024-09-01",4,32);
    FF Septiembre2024 = new FF("2024-09-02","2024-09-29",4,36);
    FF Octubre2024 = new FF("2024-09-30","2024-10-27",4,40);
    FF Noviembre2024 = new FF("2024-10-28","2024-12-01",5,44);
    FF Diciembre2024 = new FF("2024-12-02","2024-12-29",4,49);
    
    FF Enero2025 = new FF("2024-12-30","2025-02-02",5,1);
    FF Febrero2025 = new FF("2025-02-03","2025-03-02",4,6);
    FF Marzo2025 = new FF("2025-03-03","2025-04-06",5,10);
    FF Abril2025 = new FF("2025-04-07","2025-05-04",4,15);
    FF Mayo2025 = new FF("2025-05-05","2025-06-01",4,19);
    FF Junio2025 = new FF("2025-06-02","2025-07-06",5,23);
    FF Julio2025 = new FF("2025-07-07","2025-08-03",4,28);
    FF Agosto2025 = new FF("2025-08-04","2025-08-31",4,32);
    FF Septiembre2025 = new FF("2025-09-01","2025-10-05",5,36);
    FF Octubre2025 = new FF("2025-10-06","2025-11-02",4,41);
    FF Noviembre2025 = new FF("2025-11-03","2025-11-30",4,45);
    FF Diciembre2025 = new FF("2025-12-01","2025-12-28",4,49);
    
    FF Enero2026 = new FF("2025-12-29","2026-02-01",5,1);
    FF Febrero2026 = new FF("2026-02-02","2026-03-01",4,6);
    FF Marzo2026 = new FF("2026-03-02","2026-04-05",5,10);
    FF Abril2026 = new FF("2026-04-06","2026-05-03",4,15);
    FF Mayo2026 = new FF("2026-05-04","2026-05-31",4,19);
    FF Junio2026 = new FF("2026-06-01","2026-07-05",5,23);
    FF Julio2026 = new FF("2026-07-06","2026-08-02",4,28);
    FF Agosto2026 = new FF("2026-08-03","2026-08-30",4,32);
    FF Septiembre2026 = new FF("2026-08-31","2026-10-04",5,36);
    FF Octubre2026 = new FF("2026-10-05","2026-11-01",4,41);
    FF Noviembre2026 = new FF("2026-11-02","2026-11-29",4,45);
    FF Diciembre2026 = new FF("2026-11-30","2026-12-27",4,49);

    public Fechas(int mes, int año){
        if(mes == 1 && año == 2023){
            fecha = Enero2023;
        }else if(mes == 2 && año == 2023){
            fecha = Febrero2023;
        }else if(mes == 3 && año == 2023){
            fecha = Marzo2023;
        }else if(mes == 4 && año == 2023){
            fecha = Abril2023;
        }else if(mes == 5 && año == 2023){
            fecha = Mayo2023;
        }else if(mes == 6 && año == 2023){
            fecha = Junio2023;
        }else if(mes == 7 && año == 2023){
            fecha = Julio2023;
        }else if(mes == 8 && año == 2023){
            fecha = Agosto2023;
        }else if(mes == 9 && año == 2023){
            fecha = Septiembre2023;
        }else if(mes == 10 && año == 2023){
            fecha = Octubre2023;
        }else if(mes == 11 && año == 2023){
            fecha = Noviembre2023;
        }else if(mes == 12 && año == 2023){
            fecha = Diciembre2023;
        }else if(mes == 1 && año == 2024){
            fecha = Enero2024;
        }else if(mes == 2 && año == 2024){
            fecha = Febrero2024;
        }else if(mes == 3 && año == 2024){
            fecha = Marzo2024;
        }else if(mes == 4 && año == 2024){
            fecha = Abril2024;
        }else if(mes == 5 && año == 2024){
            fecha = Mayo2024;
        }else if(mes == 6 && año == 2024){
            fecha = Junio2024;
        }else if(mes == 7 && año == 2024){
            fecha = Julio2024;
        }else if(mes == 8 && año == 2024){
            fecha = Agosto2024;
        }else if(mes == 9 && año == 2024){
            fecha = Septiembre2024;
        }else if(mes == 10 && año == 2024){
            fecha = Octubre2024;
        }else if(mes == 11 && año == 2024){
            fecha = Noviembre2024;
        }else if(mes == 12 && año == 2024){
            fecha = Diciembre2024;
        }else if(mes == 1 && año == 2025){
            fecha = Enero2025;
        }else if(mes == 2 && año == 2025){
            fecha = Febrero2025;
        }else if(mes == 3 && año == 2025){
            fecha = Marzo2025;
        }else if(mes == 4 && año == 2025){
            fecha = Abril2025;
        }else if(mes == 5 && año == 2025){
            fecha = Mayo2025;
        }else if(mes == 6 && año == 2025){
            fecha = Junio2025;
        }else if(mes == 7 && año == 2025){
            fecha = Julio2025;
        }else if(mes == 8 && año == 2025){
            fecha = Agosto2025;
        }else if(mes == 9 && año == 2025){
            fecha = Septiembre2025;
        }else if(mes == 10 && año == 2025){
            fecha = Octubre2025;
        }else if(mes == 11 && año == 2025){
            fecha = Noviembre2025;
        }else if(mes == 12 && año == 2025){
            fecha = Diciembre2025;
        }else if(mes == 1 && año == 2026){
            fecha = Enero2026;
        }else if(mes == 2 && año == 2026){
            fecha = Febrero2026;
        }else if(mes == 3 && año == 2026){
            fecha = Marzo2026;
        }else if(mes == 4 && año == 2026){
            fecha = Abril2026;
        }else if(mes == 5 && año == 2026){
            fecha = Mayo2026;
        }else if(mes == 6 && año == 2026){
            fecha = Junio2026;
        }else if(mes == 7 && año == 2026){
            fecha = Julio2026;
        }else if(mes == 8 && año == 2026){
            fecha = Agosto2026;
        }else if(mes == 9 && año == 2026){
            fecha = Septiembre2026;
        }else if(mes == 10 && año == 2026){
            fecha = Octubre2026;
        }else if(mes == 11 && año == 2026){
            fecha = Noviembre2026;
        }else if(mes == 12 && año == 2026){
            fecha = Diciembre2026;
        }else{
            JOptionPane.showMessageDialog(null, "No hay registro de esta fecha","Error",JOptionPane.ERROR_MESSAGE);
            fecha = null;
        }
    }
    
    public String getFechaInicio(){
        return fecha.getFechaInicio();
    }
    
    public String getFechaFinal(){
        return fecha.getFechaFinal();
    }
    
    public int getNumeroSemanas(){
        return fecha.getSemanas();
    }
    
    public int getEmpiesa(){
        return fecha.getEmpieza();
    }
    
}
