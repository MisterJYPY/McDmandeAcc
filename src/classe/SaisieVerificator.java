/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

/**
 *
 * @author Mr_JYPY
 */
public class SaisieVerificator {
    
    
    
 public static void veritierLabel(JTextField text,char a)
  {
 String lesCaracteresNonVoulu = "[(, ,=,/,),ฐ,+,*,',:, ,้,&,่,--,_,๙,$,^^,\\,\",?,!,็,ฒ,.,ฃ,จจ,%,ต,{,},#,ง,;,<,>,เ,[a-z,A-Z]]";   
 String m=String.valueOf(text.getText());
     //char a=evt.getKeyChar();
     //notre classe paterne pour compiler la plage de caracterenon esires
     boolean t=false;
     while(!t){
         if(!m.isEmpty()){
    Pattern regPat = Pattern.compile(lesCaracteresNonVoulu );
 //ici c'est pour voir si  notre pattern comiler peut contenir les caracteres non desires
    Matcher matcher = regPat.matcher(m);
   //s'il a trouve
    int nombreElement=m.length();
      String nouveauText=m.substring(0,nombreElement-1);
//       System.out.println("non je veux pas sa ais :"+nouveauText);
    if (matcher.find()){
        m=nouveauText;
      
      }else{
        t=true;
    }
    text.setText("");
     text.setText(m);
     }else{
            t=true; 
         }
     }
  } 
}
