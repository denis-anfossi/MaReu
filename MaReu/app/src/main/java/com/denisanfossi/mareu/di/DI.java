package com.denisanfossi.mareu.di;

import com.denisanfossi.mareu.service.DummyMeetingsApiService;
import com.denisanfossi.mareu.service.MeetingsApiService;

/**
 * Dependency injector to get instances of services
 */
public class DI {
    private static MeetingsApiService sMeetingsApiService = new DummyMeetingsApiService();

    /**
     * get instance of @{@link MeetingsApiService}
     *
     * @return @{@link MeetingsApiService}
     */
    public static MeetingsApiService getMeetingsApiService() {
        return sMeetingsApiService;
    }

    /**
     * get a new instance of @{@link MeetingsApiService}
     *
     * @return @{@link MeetingsApiService}
     */
    public static MeetingsApiService getNewInstanceApiService() {
        sMeetingsApiService = new DummyMeetingsApiService();
        return sMeetingsApiService;
    }
}
