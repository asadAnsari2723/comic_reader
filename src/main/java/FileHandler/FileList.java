/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FileHandler;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class FileList<T extends File> implements Serializable {
    private ArrayList<T> tempList;
    private LinkedHashSet<T> mainList;

    public LinkedHashSet<T> getMainList() {
        return mainList;
    }
    public FileList() {
        mainList = new LinkedHashSet<>();
        tempList = new ArrayList<>();
    }

   public void add(T item){
       if(mainList.add(item))
           tempList.add(item);
   }
   
   public void addAll(T[] array)
   {
       for(T item : array){
           add(item);
       }
   }
   
   public void clearNewlyAddedItems(){
    tempList.clear();
   }
   
   public void remove(Object item){
       mainList.remove(item);
   }
   
   public ArrayList<T> getNewAddedItems(){
       return tempList;
   }

}
