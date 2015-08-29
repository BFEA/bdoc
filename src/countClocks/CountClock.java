package countClocks;

/**
 *
 * @author Altynbek
 * 
 */
public class CountClock {
    
    public CountClock(){
        
    }
    
    
    public Integer getCretits(Integer vsegoChas){
        
        Integer credits = 0;
        credits = vsegoChas/30;
        return credits;
    }
    
    public Integer getSRS(Integer vsegoChas, Integer auditornyi){
        
        Integer srs = 0;
        srs = vsegoChas - auditornyi;      
        return srs;
    }
      
    public static void main(String[] args) {
        CountClock cc = new CountClock();
        Integer cr = cc.getCretits(99);
        Integer srs = cc.getSRS(150, 64);
        System.out.println(cr);
        System.out.println(srs );
    }
    
}
