/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import classe.OperationCartonmc;
import java.util.Collection;
import java.util.Date;
/**
 *
 * @author Mr_JYPY
 * @since Le 16 janvier 2017
 * @version V.1.1a.17
 */
public class md_cartonmc {
    
    public int GetStocksmc(){
       int st=0;
       return st;
            }
    public static Collection<OperationCartonmc> listeEntreeMc(String type){
       Collection<OperationCartonmc> carton = null;
       return carton;
                      }
    public static  Collection<OperationCartonmc> listeSortie(String type){
      Collection<OperationCartonmc> carton = null;
       return carton;
                     }
    public static Collection<OperationCartonmc> listeEntreeParDate(Date date){
       Collection<OperationCartonmc> carton = null;
       return carton;
                     }
    public static Collection<OperationCartonmc> listeSortieParDate(Date date){
       Collection<OperationCartonmc> carton = null;
       return carton;
                    }
    public static boolean creerEntree(OperationCartonmc carton){
             boolean estFait=false;
             return estFait;
                    }
    public static boolean creerUneSortie(OperationCartonmc carton){
               boolean estFait=false;
               return estFait;
                    } 
    public static Collection<OperationCartonmc> rechercherProductionMensuel(String Mois){
          Collection<OperationCartonmc> carton = null;
               return carton;
                   }
}
