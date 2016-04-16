/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zulvan Firdaus
 */
public class Programmer extends Orang implements Serializable {
    private List<Tugas> tugas = new ArrayList();

    public Programmer(String username, String password, String id, String nama, String posisi) {
        super(username, password, id, nama, posisi);
    }

   public int getJumlahTugas(){
       return tugas.size();
   }

    public List<Tugas> getTugas() {
        return tugas;
    }
   
   public void tambahtugas(Tugas t){
       tugas.add(t);
   }
   
   public Boolean hapustugas(Tugas t){
       if (t.isStatus()){
           tugas.remove(t);
           return true;
       }
       else {
           return false;
       }
   }
   
   public void gantistatus(int i){
       tugas.get(i).setStatus(true);
   }
   
   public Tugas getTugas(int i){
       return tugas.get(i);
   }
}
