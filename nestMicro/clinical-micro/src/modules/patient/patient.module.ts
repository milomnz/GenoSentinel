import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { Patient } from './entity/patient.entity';
import { PatientService } from './service/patient.service';

@Module({
    imports: [
        TypeOrmModule.forFeature([Patient])
    ],
    controllers: [],
    providers: [PatientService],
})
export class PatientModule {}