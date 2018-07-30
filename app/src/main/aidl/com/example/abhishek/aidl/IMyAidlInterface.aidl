// IMyAidlInterface.aidl

package com.example.abhishek.aidl;

// Declare any non-default types here with import statements
import com.example.abhishek.aidl.Product;
interface IMyAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void addProduct(String name , int quantity, float cost);
       Product getProduct(String name);
     }
