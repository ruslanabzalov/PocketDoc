package abzalov.ruslan.pocketdoc.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import abzalov.ruslan.pocketdoc.data.clinics.Clinic;
import abzalov.ruslan.pocketdoc.data.clinics.ClinicsDao;
import abzalov.ruslan.pocketdoc.data.records.Record;
import abzalov.ruslan.pocketdoc.data.records.RecordsDao;
import abzalov.ruslan.pocketdoc.data.specialities.SpecialitiesDao;
import abzalov.ruslan.pocketdoc.data.specialities.Speciality;
import abzalov.ruslan.pocketdoc.data.stations.Station;
import abzalov.ruslan.pocketdoc.data.stations.StationDao;

@Database(entities = {Speciality.class, Station.class, Clinic.class, Record.class},
        version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SpecialitiesDao specialityDao();

    public abstract StationDao stationDao();

    public abstract ClinicsDao clinicsDao();

    public abstract RecordsDao recordDao();
}
