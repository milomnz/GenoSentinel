import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Patient } from './entity/patient.entity';
import { PatientService } from './service/patient.service';
import { PatientController } from './controller/patient.controller';

@Module({
    imports: [
        TypeOrmModule.forFeature([Patient])
    ],
    controllers: [PatientController],
    providers: [PatientService],
})
export class PatientModule {}