import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { PatientModule } from './modules/patient/patient.module';
import { Patient } from './modules/patient/entity/patient.entity';
@Module({
  imports: [
    TypeOrmModule.forRoot({
      type: 'mysql', 
      host: 'localhost',
      port: 3000,
      username: 'nestDbUser',
      password: '123456',
      database: 'nombre_db',
      entities: [Patient], // Agrega tus entidades aquí
      synchronize: false, // Solo para desarrollo (crea tablas automáticamente)
    }),
    PatientModule
  ],
  controllers: [],
  providers: [],
})
export class AppModule {}