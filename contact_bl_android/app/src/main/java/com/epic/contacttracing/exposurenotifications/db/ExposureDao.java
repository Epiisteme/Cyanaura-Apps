package com.epic.contacttracing.exposurenotifications.db;
import com.google.android.gms.nearby.exposurenotification.ExposureWindow;

import androidx.room.*;
@Dao
abstract class ExposureDao {

    @Query("SELECT * FROM ExposureEntity ORDER BY date_millis_since_epoch DESC")
    abstract  MutableList<ExposureEntity>  getAll() ;

    @Query("SELECT * FROM ExposureEntity ORDER BY date_millis_since_epoch DESC")
    abstract Flow<List<ExposureEntity>> getAllLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract  void upsertAsync( List<ExposureEntity?> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void upsert(ExposureEntity entity);

    @Query("DELETE FROM ExposureEntity")
    abstract void deleteAll();

    /**
     * Adds missing exposures based on the current windows state.
     *
     * @param exposureWindows the [ExposureWindow]s
     * @return if any exposure was added
     */
    @Transaction
    protected boolean  refreshWithExposureWindows(List<ExposureWindow> exposureWindows ) {
        // Keep track of the exposures already handled and remove them when we find matching windows.
        MutableList<ExposureEntity> exposureEntities = getAll();


        for (exposureWindow : exposureWindows) {
            boolean found = false;
            for (i : exposureEntities.indices) {
                if (exposureEntities[i].dateMillisSinceEpoch == exposureWindow.dateMillisSinceEpoch
                ) {
                    exposureEntities.removeAt(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                // No existing ExposureEntity with the given date, must add an entity for this window.
                somethingAdded = true;
                upsert(
                        ExposureEntity(dateMillisSinceEpoch = exposureWindow.dateMillisSinceEpoch)
                );
            }
        }

}