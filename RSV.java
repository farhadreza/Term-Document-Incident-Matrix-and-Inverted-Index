
public class RSV implements Comparable<RSV>
{
    String doc;
    double rsvVal;
    
    RSV()
    {
        this.doc = "";
        this.rsvVal = 0.0;
    }
    
    RSV(RSV pl)
    {
        this.doc = pl.doc;
        this.rsvVal = pl.rsvVal;
    }
    
    @Override
    public int compareTo(RSV o)
    {
        if (this.rsvVal >= o.rsvVal)
            return -1;
        else
            return 1;
    }
}
