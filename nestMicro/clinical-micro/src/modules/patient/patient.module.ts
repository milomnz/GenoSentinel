import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Patient } from './domain/entities/patient.entity';
import { PatientService } from './patient.service';

@Module({
    imports: [
        TypeOrmModule.forFeature([Patient])
    ],
    controllers: [],
    providers: [PatientService],
})
export class PatientModule {}