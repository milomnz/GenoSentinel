import { Injectable, NotFoundException } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { Repository } from 'typeorm';
import { TumorType } from '../entities/TumorType.entity';
import { CreateTumorTypeDto } from '../dto/createandUpdate-tumorType';


@Injectable()
export class TumorTypeService {
    constructor(
        @InjectRepository(TumorType)
        private tumorTypeRepository: Repository<TumorType>,
    ) {}

    findAll(): Promise<TumorType[]> {
        return this.tumorTypeRepository.find();
    }

    async findById(id: number): Promise <TumorType>{
        const tumorType = await this.tumorTypeRepository.findOneBy({id});
        if(!tumorType){
            throw new NotFoundException('Tumor Type with ID ${id} not found');
        }
        return tumorType;
    }

    async create(dto: CreateTumorTypeDto): Promise<TumorType> {
        const newTumorType = this.tumorTypeRepository.create(dto);
    return this.tumorTypeRepository.save(newTumorType);
  }


  async updatePartial(id: number, updateDto: UpdateTumorTypeDto): Promise<TumorType> {
    const result = await this.tumorTypeRepository.update(id, updateDto);

    if (result.affected === 0) {
        throw new NotFoundException(`Tumor type with ID ${id} not found`);
    }
    return this.findById(id);
}

async delete(id: number): Promise<void> {
    const result = await this.tumorTypeRepository.delete(id);
    if (result.affected === 0) {
        throw new NotFoundException(`Tumor Type with ID ${id} not found`);
    }
}
}
