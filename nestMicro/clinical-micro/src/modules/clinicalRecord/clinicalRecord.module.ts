import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ClinicalRecord } from './entities/ClinicalRecord.entity';
import { ClinicalRecordService } from './services/clinicalRecord.service';
import { Patient } from '../patient/entity/patient.entity';
import { ClinicalRecordController } from './controllers/clinicalRecord.controller';


@Module({
    imports: [
        TypeOrmModule.forFeature([ClinicalRecord, Patient])
    ],
    controllers: [ClinicalRecordController],
    providers: [ClinicalRecordService],
})
export class ClinicalRecordModule{}