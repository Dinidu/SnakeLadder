package sample;

public class Pair
{
    private final Integer x;
    private final Integer y;

    public Pair( Integer f, Integer s )
    {
        this.x = f;
        this.y = s;

    }

    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }

    @Override
    public boolean equals( Object oth )
    {
        if ( this == oth )
        {
            return true;
        }
        if ( oth == null || !(getClass().isInstance( oth )) )
        {
            return false;
        }
        Pair other = getClass().cast( oth );
        return (x == null? other.x == null : x.equals( other.x))
                && (y == null? other.y == null : y.equals( other.y));
    }

}