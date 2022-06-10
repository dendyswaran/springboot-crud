package com.deloitte.baseapp.commons.tModules;

public interface TGenericEntity<T,ID>{
    void update(T source);
    ID getId();
    T createNewInstance();
}
