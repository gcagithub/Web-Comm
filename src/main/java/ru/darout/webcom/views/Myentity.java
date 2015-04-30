package ru.darout.webcom.views;

import javax.persistence.*;
import org.joda.time.LocalDate;

@Entity
@Table(name = "myentities")
public class Myentity {

    @Id
    @GeneratedValue
    private long id;

    
    @Column(name = "myattr", length = 10)
    private String myattr;
    

    public long getId() {
        return id;
    }

    
    public String getMyattr() {
        return myattr;
    }

    public void setMyattr(String myattr) {
        this.myattr = myattr;
    }
    

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Myentity)) return false;
        Myentity that = (Myentity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
