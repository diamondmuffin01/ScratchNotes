package moviefan;

public class FavoritesManager
{
    private String favMovie = "The Dark Knight";
    private String favActor = "Heath Ledger";

    public boolean checkFavoriteMovie(String input)
    {
       if(input.equals(favMovie))
       {return true;}

       else
       {return false;}
    }

    public boolean checkFavoriteActor(String input)
    {
        if(input.equals(favActor))
        {return true;}

        else
        {return false;}
    }



}
