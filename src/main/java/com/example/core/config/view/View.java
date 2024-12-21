package com.example.core.config.view;



import java.util.List;

public interface View<T> {
     T saisie();
     void affiche(List<T> datas);
     void affiche(T data);
     
     void delete();
}
