import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { ConfigModule } from '@nestjs/config';
import { PatientModule } from './modules/patient/patient.module';
import { Patient } from './modules/patient/entity/patient.entity';
import { ClinicalRecordModule } from './modules/clinicalRecord/clinicalRecord.module';
import { tumorTypeModule } from './modules/tumorType/tumorTypemodule';
import { ClinicalRecord } from './modules/clinicalRecord/entities/ClinicalRecord.entity';
import { TumorType } from './modules/tumorType/entities/TumorType.entity';

@Module({
  imports: [
    ConfigModule.forRoot({
      isGlobal: true,
    }),

    TypeOrmModule.forRoot({
      type: 'mysql',
      host: process.env.DB_HOST,
      port: Number(process.env.DB_PORT),
      username: process.env.DB_USERNAME,
      password: process.env.DB_PASSWORD,
      database: process.env.DB_NAME,
      entities: [Patient, ClinicalRecord, TumorType],
      synchronize: false,
    }),
    PatientModule,
    ClinicalRecordModule,
    tumorTypeModule
  ],
})
export class AppModule {}
