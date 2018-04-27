package com.inquid.inquidclient_servisum;

import java.util.List;

public class BillRevision {
    private List<Integer> codes;
    private List<Integer> quantities;

    public List<Integer> getCodes() {
        return codes;
    }

    public void setCodes(List<Integer> codes) {
        this.codes = codes;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public void addQuantities(Integer val){
        this.quantities.add(val);
    }

    public void addCodes(Integer val){
        this.quantities.add(val);
    }
}
