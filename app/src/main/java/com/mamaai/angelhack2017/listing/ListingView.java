package com.mamaai.angelhack2017.listing;

import com.mamaai.angelhack2017.model.Worker;

import java.util.List;

/**
 * Created by mkpazon on 01/07/2017.
 */

interface ListingView {
    void populateRecyclerView(List<Worker> workers);

    void startDetailsActivity(Worker worker);
}
