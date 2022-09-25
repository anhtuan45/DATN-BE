package com.ws.masterserver.utils.seeder;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class BatchSeeder implements Seeder {

    private final Seeder[] seeders = new Seeder[]{
            new ChainSeeder(),
    };

    public BatchSeeder() throws NoSuchAlgorithmException {
    }

    @Override
    public void seed() {
        Arrays.asList(seeders).forEach(Seeder::seed);
    }
}
