package dataCollection;

import com.github.koraktor.steamcondenser.exceptions.SteamCondenserException;
import com.github.koraktor.steamcondenser.steam.community.SteamId;

/**
 * Created by Anand on 9/26/2016.
 */
public class DataCondenser {
    public static void main(String[] args) {
        try {
            SteamId id = SteamId.create(76561198046966189L);
            System.out.println(id);
        } catch (SteamCondenserException e) {
            e.printStackTrace();
        }
    }
}
