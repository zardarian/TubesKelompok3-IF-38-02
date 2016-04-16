/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubeskita;

import View.Register;
import View.Home_Manager;
import Model.Application;
import Model.Manager;
import Model.Orang;
import Model.Programmer;
import Model.Proyek;
import Model.Tugas;
import View.CreateProjectManager;
import View.CreateTugasManager;
import View.EditProfilM;
import View.EditStatus;
import View.HomeProgrammer;
import View.RemoveTugasPro;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import View.EditProfilPro;
import View.GUI_Login;
import View.RemoveProjectManager;
import View.RemoveTugasManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zulvan Firdaus
 */
public class Controller implements ActionListener {

    private GUI_Login l;
    private HomeProgrammer hp;
    private Home_Manager hm;
    private Application a;
    private CreateProjectManager cp;
    private CreateTugasManager ct;
    private EditProfilM em;
    private EditProfilPro ep;
    private EditStatus es;
    private Register r;
    private RemoveProjectManager rp;
    private RemoveTugasManager rt;
    private RemoveTugasPro rtp;
    Orang o = null;
    Manager m;
    Programmer p;
    private Object view;

    public Controller() throws IOException, ClassNotFoundException {
        a = new Application();
        a.start();
        l = new GUI_Login();
        l.setTitle("LOGIN");
        l.setLocationRelativeTo(null);
        l.addActionListener(this);
        l.setVisible(true);
        view = l;
    }

    public Orang login() throws IOException {
        return a.login(l.getInputUsername(), l.getInputPassword());
    }

    public Orang logIn() {
        try {
            Orang orang = login();
            if (orang == null) {
                return null;
            } else {
                return orang;
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (view instanceof GUI_Login) {
            if (source.equals(l.getLogin())) {
                o = logIn();
                if (o == null) {
                    l.logingagal();
                } else {
                    l.dispose();
                    if (o instanceof Manager) {
                        m = (Manager) o;
                        l.dispose();
                        goToHomeManager(m);
                    } else {
                        p = (Programmer) o;
                        l.dispose();
                        goToHomeProgrammer(p);
                    }
                }
            }
        } else if (view instanceof Home_Manager) {
            if (source.equals(hm.getProfil())) {
                hm.dispose();
                goToEditProfil(m);
            } else if (source.equals(hm.getCreateProject())) {
                goToCreateProject(m);
                hm.dispose();
            } else if (source.equals(hm.getCreateTask())) {
                hm.dispose();
                goToCreateTugas(m);
            } else if (source.equals(hm.getRegister())) {
                hm.dispose();
                goToRegister(m);
            } else if (source.equals(hm.getRemoveProject())) {
                hm.dispose();
                goToRemoveProject(m);
            } else if (source.equals(hm.getRemoveTask())) {
                hm.dispose();
                goToRemoveTugas(m);
            } else if (source.equals(hm.getLogout())) {
                goToLogin();
                hm.dispose();

            }
        } else if (view instanceof CreateProjectManager) {
            if (source.equals(cp.getCreateTask())) {
                cp.dispose();
                goToCreateTugas(m);
            } else if (source.equals(cp.getProfilcp())) {
                cp.dispose();
                goToEditProfil(m);
            } else if (source.equals(cp.getHome())) {
                cp.dispose();
                goToHomeManager(m);
            } else if (source.equals(cp.getRegister())) {
                cp.dispose();
                goToRegister(m);
            } else if (source.equals(cp.getRemoveProject())) {
                cp.dispose();
                goToRemoveProject(m);
            } else if (source.equals(cp.getRemoveTask())) {
                cp.dispose();
                goToRemoveTugas(m);
            } else if (source.equals(cp.getCreate())) {
                LocalDate tgl = a.UbahTanggal(cp.getInputDeadline());
                if (tgl != null) {
                    cp.createsukses();
                    m.createProyek(a.UbahTanggal(cp.getInputDeadline()), cp.getInputNama(), cp.getInputID());
                    try {
                        a.save();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    cp.gagal();
                }
            } else if (source.equals(cp.getLogout())) {
                cp.dispose();
                goToLogin();
            }
        } else if (view instanceof CreateTugasManager) {

            if (source.equals(ct.getCreateProject())) {
                ct.dispose();
                goToCreateProject(m);
            } else if (source.equals(ct.getProfil())) {
                ct.dispose();
                goToEditProfil(m);
            } else if (source.equals(ct.getHome())) {
                ct.dispose();
                goToHomeManager(m);
            } else if (source.equals(ct.getRegister())) {
                ct.dispose();
                goToRegister(m);
            } else if (source.equals(ct.getRemoveProject())) {
                ct.dispose();
                goToRemoveProject(m);
            } else if (source.equals(ct.getRemoveTask())) {
                ct.dispose();
                goToRemoveTugas(m);
            } else if (source.equals(ct.getCreate())) {
                int i = a.cariintProject(m, ct.getInputIDP());
                LocalDate tgl = a.UbahTanggal(ct.getInputDeadline());
                Programmer prog = a.cariPro(ct.getInput_IDProg());
                if ((i != 999) && (tgl != null) && (prog != null)) {
                    m.getProyek(i).createTugas(ct.getInputID(), ct.getInputNama(), prog, tgl);
                    ct.createsukses();
                    try {
                        a.save();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    ct.gagal();
                }
            } else if (source.equals(ct.getLogout())) {
                ct.dispose();
                goToLogin();
            }
        } else if (view instanceof EditProfilM) {
            if (source.equals(em.getCreateProject())) {
                em.dispose();
                goToCreateProject(m);
            } else if (source.equals(em.getCreateTask())) {
                em.dispose();
                goToCreateTugas(m);
            } else if (source.equals(em.getHome())) {
                em.dispose();
                goToHomeManager(m);
            } else if (source.equals(em.getRegister())) {
                em.dispose();
                goToRegister(m);
            } else if (source.equals(em.getRemoveProject())) {
                em.dispose();
                goToRemoveProject(m);
            } else if (source.equals(em.getRemoveTask())) {
                em.dispose();
                goToRemoveTugas(m);
            } else if (source.equals(em.getSave())) {
                if (m.getPassword().equals(em.getInputPassword())) {
                    m.setNama(em.getInputNama());
                    m.setPassword(em.getInputPasswordBaru());
                    try {
                        a.save();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    em.savesukses();
                } else {
                    em.gagal();
                }
            } else if (source.equals(em.getLogout())) {
                em.dispose();
                goToLogin();
            }

        } else if (view instanceof Register) {
            if (source.equals(r.getCreateProject())) {
                r.dispose();
                goToCreateProject(m);
            } else if (source.equals(r.getCreateTask())) {
                r.dispose();
                goToCreateTugas(m);
            } else if (source.equals(r.getProfil())) {
                r.dispose();
                goToEditProfil(m);
            } else if (source.equals(r.getHome())) {
                r.dispose();
                goToHomeManager(m);
            } else if (source.equals(r.getRemoveProject())) {
                r.dispose();
                goToRemoveProject(m);
            } else if (source.equals(r.getRemoveTask())) {
                r.dispose();
                goToRemoveTugas(m);
            } else if (source.equals(r.getCreate())) {
                if ((r.getManager()) && (r.getProgrammer())) {
                    r.gagal();
                } else if (r.getManager()) {
                    a.getOrang().add(new Manager(r.getInputUsername(), r.getInputPassword(), r.getInputID(), r.getInputNama(), "Manager"));
                    r.savesukses();
                    try {
                        a.save();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (r.getProgrammer()) {
                    a.getOrang().add(new Programmer(r.getInputUsername(), r.getInputPassword(), r.getInputID(), r.getInputNama(), "Programmer"));
                    r.savesukses();
                    try {
                        a.save();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } else if (source.equals(r.getLogout())) {
                r.dispose();
                goToLogin();
            }
        } else if (view instanceof RemoveProjectManager) {
            if (source.equals(rp.getCreateProject())) {
                rp.dispose();
                goToCreateProject(m);
            } else if (source.equals(rp.getCreateTask())) {
                rp.dispose();
                goToCreateTugas(m);
            } else if (source.equals(rp.getProfil())) {
                rp.dispose();
                goToEditProfil(m);
            } else if (source.equals(rp.getHome())) {
                rp.dispose();
                goToHomeManager(m);
            } else if (source.equals(rp.getRegister())) {
                rp.dispose();
                goToRegister(m);
            } else if (source.equals(rp.getRemoveTask())) {
                rp.dispose();
                goToRemoveTugas(m);
            } else if (source.equals(rp.getSearch())) {
                try {
                    int i = a.cariintProject(m, rp.getInputSearch());
                    if (i == 999) {
                        rp.carigagal();
                    } else {
                        rp.getTable().setValueAt(m.getProyek(i).getIdProyek(), 0, 0);
                        rp.getTable().setValueAt(m.getProyek(i).getNamaProyek(), 0, 1);
                        rp.getTable().setValueAt(m.getProyek(i).jumlahTugas(), 0, 2);
                        rp.getTable().setValueAt(m.getProyek(i).getStrDeadline(), 0, 3);
                        if (m.getProyek(i).isStatusProyek()) {
                            rp.getTable().setValueAt("Done", i, 4);
                        } else {
                            rp.getTable().setValueAt("On Process", i, 4);
                        }
                    }
                } catch (Exception ex) {
                }

            } else if (source.equals(rp.getRemove())) {
                try {
                    int i = a.cariintProject(m, rp.getInputSearch());;
                    if (i == 999) {
                        rp.removegagal();
                    } else {
                        m.hapusProyek(m.getProyek(i));
                        rp.removesukses();
                        try {
                            a.save();
                        } catch (IOException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (Exception ex) {
                }
            } else if (source.equals(rp.getLogout())) {
                rp.dispose();
                goToLogin();
            }
        } else if (view instanceof RemoveTugasManager) {
            if (source.equals(rt.getCreateProject())) {
                rt.dispose();
                goToCreateProject(m);
            } else if (source.equals(rt.getCreateTask())) {
                rt.dispose();
                goToCreateTugas(m);
            } else if (source.equals(rt.getProfil())) {
                rt.dispose();
                goToEditProfil(m);
            } else if (source.equals(rt.getHome())) {
                rt.dispose();
                goToHomeManager(m);
            } else if (source.equals(rt.getRemoveProject())) {
                rt.dispose();
                goToRemoveProject(m);
            } else if (source.equals(rt.getRegister())) {
                rt.dispose();
                goToRegister(m);
            } else if (source.equals(rt.getSearch())) {
                try {
                    int i = a.cariintProject(m, rt.getInputSearchProject());
                    int j = a.cariTugas(m.getProyek(i), rt.getInputSearchTugas());
                    if (i == 999 || j == 999) {
                        rt.carigagal();
                    } else {
                        rt.getTable().setValueAt(m.getProyek(i).getTugas(j).getId(), 0, 0);
                        rt.getTable().setValueAt(m.getProyek(i).getTugas(j).getNamaTugas(), 0, 1);
                        rt.getTable().setValueAt(m.getProyek(i).getTugas(j).getPelaksana().getNama(), 0, 2);
                        rt.getTable().setValueAt(m.getProyek(i).getTugas(j).getStrDeadline(), 0, 3);
                        if (m.getProyek(i).getTugas(j).isStatus()) {
                            rt.getTable().setValueAt("Done", 0, 4);
                        } else {
                            rt.getTable().setValueAt("On Process", 0, 4);
                        }
                    }
                } catch (Exception ex) {
                    rt.carigagal();
                }
            } else if (source.equals(rt.getRemove())) {
                try {
                    int i = a.cariintProject(m, rt.getInputSearchProject());
                    int j = a.cariTugas(m.getProyek(i), rt.getInputSearchTugas());
                    if (i == 999 || j == 999) {
                        rt.carigagal();
                    } else {
                        m.getProyek(i).removetugas(j);
                        rt.removesukses();
                        try {
                            a.save();
                        } catch (IOException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } catch (Exception ex) {
                }
            } else if (source.equals(rt.getLogout())) {
                rt.dispose();
                goToLogin();
            }
        } else if (view instanceof HomeProgrammer) {
            if (source.equals(hp.getProfil())) {
                hp.dispose();
                goToEditProg(p);
            } else if (source.equals(hp.getEdit())) {
                hp.dispose();
                goToEditStatus(p);
            } else if (source.equals(hp.getRemoveTask())) {
                hp.dispose();
                goToRemoveTugasPro(p);
            } else if (source.equals(hp.getLogout())) {
                hp.dispose();
                goToLogin();
            }
        } else if (view instanceof EditProfilPro) {
            if (source.equals(ep.getList())) {
                ep.dispose();
                goToHomeProgrammer(p);
            } else if (source.equals(ep.getEdit())) {
                ep.dispose();
                goToEditStatus(p);
            } else if (source.equals(ep.getRemoveTask())) {
                ep.dispose();
                goToRemoveTugasPro(p);
            } else if (source.equals(ep.getSave())) {
                if (p.getPassword().equals(ep.getInputPassword())) {
                    p.setNama(ep.getInputNama());
                    p.setPassword(ep.getInputPasswordBaru());
                    try {
                        a.save();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    ep.savesukses();
                } else {
                    ep.gagal();
                }
            } else if (source.equals(ep.getLogout())) {
                ep.dispose();
                goToLogin();
            }
        } else if (view instanceof EditStatus) {
            if (source.equals(es.getList())) {
                es.dispose();
                goToHomeProgrammer(p);
            } else if (source.equals(es.getProfil())) {
                es.dispose();
                goToEditProg(p);
            } else if (source.equals(es.getRemoveTask())) {
                es.dispose();
                goToRemoveTugasPro(p);
            } else if (source.equals(es.getSearch())) {
                try {
                    int j = a.cariTugasPro(p, es.getSearchTxt());
                    if (j == 999) {
                        es.carigagal();
                    } else {
                        es.getTable().setValueAt(p.getTugas(j).getId(), 0, 0);
                        es.getTable().setValueAt(p.getTugas(j).getNamaTugas(), 0, 1);
                        es.getTable().setValueAt(p.getTugas(j).getPelaksana().getNama(), 0, 2);
                        es.getTable().setValueAt(p.getTugas(j).getStrDeadline(), 0, 3);
                        if (p.getTugas(j).isStatus()) {
                            es.getTable().setValueAt("Done", 0, 4);
                        } else {
                            es.getTable().setValueAt("On Process", 0, 4);
                        }
                    }
                } catch (Exception ex) {
                    es.carigagal();
                }
            } else if (source.equals(es.getChange())) {
                int j = a.cariTugasPro(p, es.getSearchTxt());
                if (j != 999) {
                    p.gantistatus(j);
                    es.changesukses();
                    try {
                        a.save();
                    } catch (IOException ex) {
                        Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if (source.equals(es.getLogout())) {
                es.dispose();
                goToLogin();
            }
        } else if (view instanceof RemoveTugasPro) {
            if (source.equals(rtp.getList())) {
                rtp.dispose();
                goToHomeProgrammer(p);
            } else if (source.equals(rtp.getProfil())) {
                rtp.dispose();
                goToEditProg(p);
            } else if (source.equals(rtp.getEdit())) {
                rtp.dispose();
                goToEditStatus(p);
            } else if (source.equals(rtp.getSearch())) {
                try {
                    int j = a.cariTugasPro(p, rtp.getInputSearch());
                    if (j == 999) {
                        rtp.carigagal();
                    } else {
                        rtp.getTable().setValueAt(p.getTugas(j).getId(), 0, 0);
                        rtp.getTable().setValueAt(p.getTugas(j).getNamaTugas(), 0, 1);
                        rtp.getTable().setValueAt(p.getTugas(j).getPelaksana().getNama(), 0, 2);
                        rtp.getTable().setValueAt(p.getTugas(j).getStrDeadline(), 0, 3);
                        if (p.getTugas(j).isStatus()) {
                            es.getTable().setValueAt("Done", 0, 4);
                        } else {
                            es.getTable().setValueAt("On Process", 0, 4);
                        }
                    }
                } catch (Exception ex) {
                }
            } else if (source.equals(rtp.getLogout())) {
                rtp.dispose();
                goToLogin();
            } else if (source.equals(rtp.getRemove())) {
                int j = a.cariTugasPro(p, rtp.getInputSearch());
                if (j != 999) {
                    if (p.hapustugas(p.getTugas(j))) {
                        rtp.removesukses();
                        try {
                            a.save();
                        } catch (IOException ex) {
                            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        rtp.gagal();
                    }
                }
            }
        }
    }

    public void goToLogin() {
        l = new GUI_Login();
        l.setTitle("LOGIN");
        l.setLocationRelativeTo(null);
        l.addActionListener(this);
        l.setVisible(true);
        view = l;
    }

    public void goToCreateProject(Manager m) {
        cp = new CreateProjectManager();
        cp.setTitle("Create Project");
        cp.setLocationRelativeTo(null);
        cp.addActionListener(this);
        cp.setVisible(true);
        cp.setNamaPengguna(m.getNama());
        cp.setIDPengguna(m.getId());
        String s;
        for (int k = 0; k < m.jumlahproyek(); k++) {
            if (m.getProyek(k).notifproyek() == 1) {
                s = m.getProyek(k).getIdProyek() + " deadline besok";
                cp.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 7) {
                s = m.getProyek(k).getIdProyek() + " deadline tinggal 7 hari lagi";
                cp.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 0) {
                s = m.getProyek(k).getIdProyek() + " deadline hari ini";
                cp.getNotif().setValueAt(s, k, 0);
            }
        }
        view = cp;
    }

    public void goToEditProfil(Manager m) {
        em = new EditProfilM();
        em.setTitle("Edit Profil");
        em.setLocationRelativeTo(null);
        em.addActionListener(this);
        em.setVisible(true);
        em.setNamaPengguna(m.getNama());
        em.setIDPengguna(m.getId());
        String s;
        for (int k = 0; k < m.jumlahproyek(); k++) {
            if (m.getProyek(k).notifproyek() == 1) {
                s = m.getProyek(k).getIdProyek() + " deadline besok";
                em.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 7) {
                s = m.getProyek(k).getIdProyek() + " deadline tinggal 7 hari lagi";
                em.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 0) {
                s = m.getProyek(k).getIdProyek() + " deadline hari ini";
                em.getNotif().setValueAt(s, k, 0);
            }
        }
        view = em;
    }

    public void goToHomeManager(Manager m) {
        int i = 0;
        hm = new Home_Manager();
        hm.setTitle("Home Manager");
        hm.setLocationRelativeTo(null);
        hm.addActionListener(this);
        hm.setVisible(true);
        hm.setNamaPengguna(m.getNama());
        hm.setIDPengguna(m.getId());
        for (Proyek pyk : m.getProyek()) {
            pyk.gantistatus();
            hm.getTable().setValueAt(pyk.getIdProyek(), i, 0);
            hm.getTable().setValueAt(pyk.getNamaProyek(), i, 1);
            hm.getTable().setValueAt(pyk.jumlahTugas(), i, 2);
            hm.getTable().setValueAt(pyk.jumlahProgrammer(), i, 3);
            hm.getTable().setValueAt(pyk.getStrDeadline(), i, 4);
            if (pyk.isStatusProyek()) {
                hm.getTable().setValueAt("Done", i, 5);
            } else {
                hm.getTable().setValueAt("On Process", i, 5);
            }
            i++;
        }
        String s;
        for (int k = 0; k < m.jumlahproyek(); k++) {
            if (m.getProyek(k).notifproyek() == 1) {
                s = m.getProyek(k).getIdProyek() + " deadline besok";
                hm.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 7) {
                s = m.getProyek(k).getIdProyek() + " deadline tinggal 7 hari lagi";
                hm.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 0) {
                s = m.getProyek(k).getIdProyek() + " deadline hari ini";
                hm.getNotif().setValueAt(s, k, 0);
            }
        }
        view = hm;
    }

    public void goToCreateTugas(Manager m) {
        ct = new CreateTugasManager();
        ct.setTitle("Create Tugas");
        ct.setLocationRelativeTo(null);
        ct.addActionListener(this);
        ct.setVisible(true);
        ct.setNamaPengguna(m.getNama());
        ct.setIDPengguna(m.getId());
        String s;
        for (int k = 0; k < m.jumlahproyek(); k++) {
            if (m.getProyek(k).notifproyek() == 1) {
                s = m.getProyek(k).getIdProyek() + " deadline besok";
                ct.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 7) {
                s = m.getProyek(k).getIdProyek() + " deadline tinggal 7 hari lagi";
                ct.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 0) {
                s = m.getProyek(k).getIdProyek() + " deadline hari ini";
                ct.getNotif().setValueAt(s, k, 0);
            }
        }
        view = ct;
    }

    public void goToRegister(Manager m) {
        r = new Register();
        r.setTitle("Register");
        r.setLocationRelativeTo(null);
        r.addActionListener(this);
        r.setVisible(true);
        r.setNamaPengguna(m.getNama());
        r.setIDPengguna(m.getId());
        String s;
        for (int k = 0; k < m.jumlahproyek(); k++) {
            if (m.getProyek(k).notifproyek() == 1) {
                s = m.getProyek(k).getIdProyek() + " deadline besok";
                r.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 7) {
                s = m.getProyek(k).getIdProyek() + " deadline tinggal 7 hari lagi";
                r.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 0) {
                s = m.getProyek(k).getIdProyek() + " deadline hari ini";
                r.getNotif().setValueAt(s, k, 0);
            }
        }
        view = r;
    }

    public void goToRemoveProject(Manager m) {
        rp = new RemoveProjectManager();
        rp.setTitle("Remove Project");
        rp.setLocationRelativeTo(null);
        rp.addActionListener(this);
        rp.setVisible(true);
        rp.setNamaPengguna(m.getNama());
        rp.setIDPengguna(m.getId());
        String s;
        for (int k = 0; k < m.jumlahproyek(); k++) {
            if (m.getProyek(k).notifproyek() == 1) {
                s = m.getProyek(k).getIdProyek() + " deadline besok";
                rp.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 7) {
                s = m.getProyek(k).getIdProyek() + " deadline tinggal 7 hari lagi";
                rp.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 0) {
                s = m.getProyek(k).getIdProyek() + " deadline hari ini";
                rp.getNotif().setValueAt(s, k, 0);
            }
        }
        view = rp;
    }

    public void goToRemoveTugas(Manager m) {
        rt = new RemoveTugasManager();
        rt.setTitle("Remove Tugas");
        rt.setLocationRelativeTo(null);
        rt.addActionListener(this);
        rt.setVisible(true);
        rt.setNamaPengguna(m.getNama());
        rt.setIDPengguna(m.getId());
        String s;
        for (int k = 0; k < m.jumlahproyek(); k++) {
            if (m.getProyek(k).notifproyek() == 1) {
                s = m.getProyek(k).getIdProyek() + " deadline besok";
                rt.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 7) {
                s = m.getProyek(k).getIdProyek() + " deadline tinggal 7 hari lagi";
                rt.getNotif().setValueAt(s, k, 0);
            } else if (m.getProyek(k).notifproyek() == 0) {
                s = m.getProyek(k).getIdProyek() + " deadline hari ini";
                rt.getNotif().setValueAt(s, k, 0);
            }
        }
        view = rt;
    }

    public void goToEditProg(Programmer p) {
        ep = new EditProfilPro();
        ep.setTitle("Edit Profil");
        ep.setLocationRelativeTo(null);
        ep.addActionListener(this);
        ep.setVisible(true);
        ep.setNamaPengguna(p.getNama());
        ep.setIDPengguna(p.getId());
        String s;
        for (int k = 0; k < p.getJumlahTugas(); k++) {
            if (p.getTugas(k).notiftugas() == 1) {
                s = p.getTugas(k).getId() + " deadline besok";
                ep.getNotif().setValueAt(s, k, 0);
            } else if (p.getTugas(k).notiftugas() == 7) {
                s = p.getTugas(k).getId() + " deadline tinggal 7 hari lagi";
                ep.getNotif().setValueAt(s, k, 0);
            } else if (p.getTugas(k).notiftugas() == 0) {
                s = p.getTugas(k).getId() + " deadline hari ini";
                ep.getNotif().setValueAt(s, k, 0);
            }
        }
        view = ep;
    }

    public void goToHomeProgrammer(Programmer p) {
        int i = 0;
        hp = new HomeProgrammer();
        hp.setTitle("Home Programmer");
        hp.setLocationRelativeTo(null);
        hp.addActionListener(this);
        hp.setVisible(true);
        hp.setNamaPengguna(p.getNama());
        hp.setIDPengguna(p.getId());
        for (Tugas t : p.getTugas()) {
            hp.getTable().setValueAt(t.getId(), i, 0);
            hp.getTable().setValueAt(t.getNamaTugas(), i, 1);
            hp.getTable().setValueAt(t.getStrDeadline(), i, 2);
            if (t.isStatus()) {
                hp.getTable().setValueAt("Done", i, 3);
            } else {
                hp.getTable().setValueAt("On Process", i, 3);
            }
            i++;
        }
        String s;
        for (int k = 0; k < p.getJumlahTugas(); k++) {
            if (p.getTugas(k).notiftugas() == 1) {
                s = p.getTugas(k).getId() + " deadline besok";
                hp.getNotif().setValueAt(s, k, 0);
            } else if (p.getTugas(k).notiftugas() == 7) {
                s = p.getTugas(k).getId() + " deadline tinggal 7 hari lagi";
                hp.getNotif().setValueAt(s, k, 0);
            } else if (p.getTugas(k).notiftugas() == 0) {
                s = p.getTugas(k).getId() + " deadline hari ini";
                hp.getNotif().setValueAt(s, k, 0);
            }
        }
        view = hp;
    }

    public void goToEditStatus(Programmer p) {
        es = new EditStatus();
        es.setTitle("Edit Status");
        es.setLocationRelativeTo(null);
        es.addActionListener(this);
        es.setVisible(true);
        es.setNamaPengguna(p.getNama());
        es.setIDPengguna(p.getId());
        String s;
        for (int k = 0; k < p.getJumlahTugas(); k++) {
            if (p.getTugas(k).notiftugas() == 1) {
                s = p.getTugas(k).getId() + " deadline besok";
                es.getNotif().setValueAt(s, k, 0);
            } else if (p.getTugas(k).notiftugas() == 7) {
                s = p.getTugas(k).getId() + " deadline tinggal 7 hari lagi";
                es.getNotif().setValueAt(s, k, 0);
            } else if (p.getTugas(k).notiftugas() == 0) {
                s = p.getTugas(k).getId() + " deadline hari ini";
                es.getNotif().setValueAt(s, k, 0);
            }
        }
        view = es;
    }

    public void goToRemoveTugasPro(Programmer p) {
        rtp = new RemoveTugasPro();
        rtp.setTitle("Remove Tugas");
        rtp.setLocationRelativeTo(null);
        rtp.addActionListener(this);
        rtp.setVisible(true);
        rtp.setNamaPengguna(p.getNama());
        rtp.setIDPengguna(p.getId());
        String s;
        for (int k = 0; k < p.getJumlahTugas(); k++) {
            if (p.getTugas(k).notiftugas() == 1) {
                s = p.getTugas(k).getId() + " deadline besok";
                rtp.getNotif().setValueAt(s, k, 0);
            } else if (p.getTugas(k).notiftugas() == 7) {
                s = p.getTugas(k).getId() + " deadline tinggal 7 hari lagi";
                rtp.getNotif().setValueAt(s, k, 0);
            } else if (p.getTugas(k).notiftugas() == 0) {
                s = p.getTugas(k).getId() + " deadline hari ini";
                rtp.getNotif().setValueAt(s, k, 0);
            }
        }
        view = rtp;
    }
}
