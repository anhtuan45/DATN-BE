package com.ws.masterserver.utils.seeder;

import javax.transaction.Transactional;

public interface Seeder {
    @Transactional
    void seed();
}
