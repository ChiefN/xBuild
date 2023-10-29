package com.example.xbulild.data;

import com.example.xbulild.util.Util;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    private String id = Util.createId();

    public boolean prevSaved;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public boolean isPrevSaved() {
        return prevSaved;
    }
    public void setPrevSaved(boolean prevSaved) {
        this.prevSaved = prevSaved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof AbstractEntity)) {
            return false;
        }

        AbstractEntity other = (AbstractEntity) o;
        // if the id is missing, return false
        if (id == null) return false;
        // equivalence by id
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }
}
