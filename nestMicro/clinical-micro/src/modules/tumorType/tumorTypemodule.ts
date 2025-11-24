import { Module } from '@nestjs/common';
import { TypeOrmModule } from '@nestjs/typeorm';
import { TumorType } from './entities/TumorType.entity';
import { TumorTypeService } from './tumorType.service';

@Module({
    imports: [
        TypeOrmModule.forFeature([TumorType])
    ],
    controllers: [],
    providers: [TumorTypeService]
})
export class tumorTypeModule{}