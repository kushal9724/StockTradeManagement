// package com.example.TradeManagement;

// package io.polygon.kotlin.sdk.sample;

// import io.polygon.kotlin.sdk.rest.*;

// public class JavaUsageSample {

//   public static void main(String[] args) throws InterruptedException {
//     PolygonRestClient client = new PolygonRestClient(polygonKey);

//     AggregatesDTO aggs = client.getAggregatesBlocking(
//       new AggregatesParametersBuilder()
//         .ticker("AAPL")
//         .timespan("day")
//         .fromDate("2023-02-01")
//         .toDate("2023-04-01")
//         .build()
//     );

//     System.out.println("Got " + aggs.getResults().size() + " results");
//   }
// }
