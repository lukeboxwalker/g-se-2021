package de.techfak.gse.lwalkenhorst.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

public class PropertyListenerSupport implements Serializable {

    private final PropertyChangeSupport propertyChangeSupport;

    public PropertyListenerSupport(final Object sourceBean) {
        this.propertyChangeSupport = new PropertyChangeSupport(sourceBean);
    }

    public void firePropertyChange(final PropertyChange propertyChange, final Object oldValue, final Object newValue) {
        this.propertyChangeSupport.firePropertyChange(propertyChange.getPropertyName(), oldValue, newValue);
    }

    public void addPropertyChangeListener(final PropertyChange propertyChange, final PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(propertyChange.getPropertyName(), listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
