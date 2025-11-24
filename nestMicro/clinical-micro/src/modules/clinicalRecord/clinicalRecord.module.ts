import { Injectable, Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ClinicalRecord } from './entities/ClinicalRecord.entity';
import { ClinicalRecordService } from './services/clinicalRecord.service';


@Module({
    imports: [
        TypeOrmModule.forFeature([ClinicalRecord])
    ],
    controllers: [],
    providers: [ClinicalRecordService],
})
export class clinicalRecordModule{}