package com.deloitte.baseapp.commons;

public interface TGenericEntity<T,ID>{
    void update(T source);
    ID getId();
    T createNewInstance();
}
