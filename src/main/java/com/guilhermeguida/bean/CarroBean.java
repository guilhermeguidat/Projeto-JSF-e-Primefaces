package com.guilhermeguida.bean;

import com.guilhermeguida.dao.CarroDao;
import com.guilhermeguida.model.Carro;
import com.guilhermeguida.util.ErroSistema;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "carroBean")
@SessionScoped
public class CarroBean {

    private Carro carro = new Carro();
    private List<Carro> carros = new ArrayList<Carro>();
    private CarroDao carroDao = new CarroDao();

    public void addCarro() {
        try {
            carroDao.salvar(carro);
            addMsg("Salvo!", "Carro salvo com sucesso!", FacesMessage.SEVERITY_INFO);
        } catch (ErroSistema e) {
            addMsg(e.getMessage(), String.valueOf(e.getCause()), FacesMessage.SEVERITY_ERROR);
        }
        carro = new Carro();
    }

    public void listarCarros() {
        try {
            carros = carroDao.buscar();
            if(carros == null || carros.size() == 0) {
                addMsg("Nenhum dado encontrado!", "Sua busca não retornou nenhum carro!", FacesMessage.SEVERITY_WARN);
            }
        } catch (ErroSistema e) {
            addMsg(e.getMessage(), String.valueOf(e.getCause()), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void editarCarro(Carro c) {
        carro = c;
    }

    public void excluirCarro(Carro c) {
        try {
            carroDao.deletar(c.getId());
            addMsg("Deletado!", "Carro deletado com sucesso!", FacesMessage.SEVERITY_INFO);

        } catch (ErroSistema e) {
            addMsg(e.getMessage(), String.valueOf(e.getCause()), FacesMessage.SEVERITY_ERROR);
        }
    }

    public void addMsg(String sumario, String detalhe, FacesMessage.Severity tipoErro) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(tipoErro, sumario, detalhe);
        context.addMessage(null, message);
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public List<Carro> getCarros() {
        return carros;
    }

    public void setCarros(List<Carro> carros) {
        this.carros = carros;
    }
}
