package com.geekbrains.tests.stubs

import com.geekbrains.tests.presenter.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class ScheduleProviderStub : SchedulerProvider {
    override fun ui(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()
}